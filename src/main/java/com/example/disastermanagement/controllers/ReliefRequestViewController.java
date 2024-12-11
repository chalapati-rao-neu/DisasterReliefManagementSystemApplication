package com.example.disastermanagement.controllers;

import com.example.disastermanagement.models.ReliefRequest;
import com.example.disastermanagement.models.ReliefRequestStatus;
import com.example.disastermanagement.services.ReliefRequestService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String listReliefRequestsAll(Model model) {
        model.addAttribute("requests", reliefRequestService.getAllReliefRequests());
        return "relief-requests/listall";
    }
    
    @GetMapping("/view")
    public String listReliefRequests(@RequestParam(value = "success", required = false) Boolean success,
                                     @RequestParam(value = "requestId", required = false) Long requestId,
                                     Model model
                                     ) {
        // Get the logged-in user's name
        String loggedInUser = (String) SecurityContextHolder.getContext().getAuthentication().getName();

        // Fetch the relief requests for the logged-in user
        List<ReliefRequest> userRequests = reliefRequestService.getReliefRequestsByRequesterName(loggedInUser);
        model.addAttribute("requests", userRequests);

        // Add success message and request ID to the model if provided
        if (Boolean.TRUE.equals(success) && requestId != null) {
            model.addAttribute("success", true);
            model.addAttribute("requestId", requestId);
        }

        return "relief-requests/list"; // Render list.html
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("reliefRequest", new ReliefRequest());
        return "relief-requests/create";
    }

    @PostMapping("/create")
    public String createReliefRequest(@ModelAttribute ReliefRequest reliefRequest) {
        String loggedInUser = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        reliefRequest.setRequesterName(loggedInUser); // Set the logged-in user as the requester
        reliefRequest.setStatus(ReliefRequestStatus.PENDING); // Default status
        reliefRequestService.createReliefRequest(reliefRequest);
        return "redirect:/relief-requests/view?success=true&requestId=" + reliefRequest.getId();
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        ReliefRequest request = reliefRequestService.getReliefRequestById(id);
        if (request != null) {
            model.addAttribute("request", request);
            model.addAttribute("statuses", ReliefRequestStatus.values());
            return "relief-requests/edit";
        }
        return "redirect:/relief-requests/view";
    }

    @PostMapping("/edit/{id}")
    public String updateReliefRequestStatus(@PathVariable Long id, @RequestParam ReliefRequestStatus status) {
        reliefRequestService.updateReliefRequestStatus(id, status);
        return "redirect:/relief-requests/view";
    }
    
 // New route for user-specific editing
    @GetMapping("/user/edit/{id}")
    public String showUserEditForm(@PathVariable Long id, Model model) {
        String loggedInUser = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        ReliefRequest request = reliefRequestService.getReliefRequestById(id);

        // Ensure the logged-in user owns the request
        if (request != null && request.getRequesterName().equals(loggedInUser)) {
            model.addAttribute("request", request);
            return "relief-requests/user-edit";
        }
        return "redirect:/relief-requests/view";
    }

    @PostMapping("/user/edit/{id}")
    public String updateUserReliefRequest(@PathVariable Long id,
                                          @RequestParam String location,
                                          @RequestParam String details) {
        String loggedInUser = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        ReliefRequest request = reliefRequestService.getReliefRequestById(id);

        // Ensure the logged-in user owns the request
        if (request != null && request.getRequesterName().equals(loggedInUser)) {
            request.setLocation(location);
            request.setDetails(details);
            reliefRequestService.updateReliefRequest(request);
        }
        return "redirect:/relief-requests/view";
    }
}