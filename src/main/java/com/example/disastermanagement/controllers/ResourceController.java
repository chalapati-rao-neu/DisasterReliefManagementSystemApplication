package com.example.disastermanagement.controllers;
import com.example.disastermanagement.models.Resource;
import com.example.disastermanagement.services.ResourceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;

   
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public String listResources(Model model) {
        model.addAttribute("resources", resourceService.getAllResources());
        return "resources/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("resource", new Resource());
        return "resources/create";
    }

    @PostMapping("/create")
    public String createResource(@ModelAttribute Resource resource) {
        resourceService.createResource(resource);
        return "redirect:/resources";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Resource resource = resourceService.getResourceById(id);
        if (resource != null) {
            model.addAttribute("resource", resource);
            return "resources/edit";
        }
        return "redirect:/resources";
    }

    @PostMapping("/edit/{id}")
    public String updateResource(@PathVariable Long id, @ModelAttribute Resource resource) {
        Resource existingResource = resourceService.getResourceById(id);
        if (existingResource != null) {
            existingResource.setName(resource.getName());
            existingResource.setQuantity(resource.getQuantity());
            existingResource.setThreshold(resource.getThreshold());
            resourceService.updateResource(existingResource);
        }
        return "redirect:/resources";
    }

    @GetMapping("/delete/{id}")
    public String deleteResource(@PathVariable Long id) {
        resourceService.deleteResourceById(id);
        return "redirect:/resources";
    }
}