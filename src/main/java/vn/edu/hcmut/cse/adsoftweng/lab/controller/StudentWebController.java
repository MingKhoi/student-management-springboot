package vn.edu.hcmut.cse.adsoftweng.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // Luu y: su dung @Controller, KHONG dung @RestController
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.hcmut.cse.adsoftweng.lab.service.StudentService;
import vn.edu.hcmut.cse.adsoftweng.lab.entity.Student;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentWebController {
    @Autowired
    private StudentService service;

    // Route: GET http://localhost:8080/students
    @GetMapping
    public String getAllStudents(
            @RequestParam(required = false) String keyword,
            Model model) {

        System.out.println("Keyword = " + keyword);

        List<Student> students;

        if (keyword != null && !keyword.isEmpty()) {
            students = service.searchByName(keyword);
        } else {
            students = service.getAll();
        }

        model.addAttribute("dsSinhVien", students);
        return "students";
    }

    @GetMapping("/{id}")
    public String getStudentDetail(@PathVariable Long id, Model model) {
        Student student = service.getById(id);
        if (student == null) {
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        return "student-detail";
    }

    @GetMapping("new")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("isEdit", false);
        return "student-form";
    }

    @GetMapping("edit/{id}")
    public String shoEditForm(@PathVariable Long id, Model model) {
        Student student = service.getById(id);
        if (student == null) {
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        return "student-form";
    }

    // @PostMapping
    // public String addStudent(@ModelAttribute Student student, BindingResult
    // result,
    // RedirectAttributes redirectAttributes) {
    // if (result.hasErrors()) {
    // return "student-form";
    // }

    // if (service.existsById(student.getId())) {
    // redirectAttributes.addFlashAttribute("error", "ID sinh viên đã tồn tại!");
    // return "redirect:/students";
    // }

    // service.save(student);
    // redirectAttributes.addFlashAttribute("success", "Thêm sinh viên thành
    // công!");
    // return "redirect:/students";
    // }

    @PostMapping("/{id}/update")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student student,
            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "student-form";
        }

        Student existingStudent = service.getById(id);
        if (existingStudent == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sinh viên!");
            return "redirect:/students";
        }

        existingStudent.setName(student.getName());
        existingStudent.setEmail((student.getEmail()));
        existingStudent.setAge(student.getAge());

        service.save(existingStudent);
        redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
        return "redirect:/students";
    }

    @PostMapping("delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Student student = service.getById(id);
        if (student == null) {
            // redirectAttributes.addFlashAttribute("error", "Không tìm thấy sinh viên!");
            return "redirect:/students";
        }
        service.deleteById(id);
        // redirectAttributes.addFlashAttribute("success", "Xóa sinh viên thành công!");
        return "redirect:/students";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Student student) {
        service.save(student);
        return "redirect:/students";
    }
}