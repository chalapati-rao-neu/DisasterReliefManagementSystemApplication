package com.example.disastermanagement.controllers;

import com.example.disastermanagement.models.ReliefRequest;
import com.example.disastermanagement.models.ReliefRequestStatus;
import com.example.disastermanagement.services.ReliefRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/relief-requests")
public class ReliefRequestViewController {

    private final ReliefRequestService reliefRequestService;

    @Autowired
    public ReliefRequestViewController(ReliefRequestService reliefRequestService) {
        this.reliefRequestService = reliefRequestService;
    }

    @GetMapping
    public String listReliefRequests(Model model) {
        model.addAttribute("requests", reliefRequestService.getAllReliefRequests());
        return "relief-requests/list";
    }

    @GetMapping("/create")
    public String showCreateForm() {
        return "relief-requests/create";
    }

    @PostMapping("/create")
    public String createReliefRequest(@ModelAttribute ReliefRequest reliefRequest) {
        reliefRequest.setStatus(ReliefRequestStatus.PENDING); // Default status
        reliefRequestService.createReliefRequest(reliefRequest);
        return "redirect:/relief-requests";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        ReliefRequest request = reliefRequestService.getReliefRequestById(id);
        if (request != null) {
            model.addAttribute("request", request);
            model.addAttribute("statuses", ReliefRequestStatus.values()); // Provide all enum values for the dropdown
            return "relief-requests/edit";
        }
        return "redirect:/relief-requests";
    }

    @PostMapping("/edit/{id}")
    public String updateReliefRequestStatus(@PathVariable Long id, @RequestParam ReliefRequestStatus status) {
        reliefRequestService.updateReliefRequestStatus(id, status);
        return "redirect:/relief-requests";
    }
}