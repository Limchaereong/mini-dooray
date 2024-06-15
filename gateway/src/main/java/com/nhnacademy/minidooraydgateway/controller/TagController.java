package com.nhnacademy.minidooraydgateway.controller;

import com.nhnacademy.minidooraydgateway.domain.Tag;
import com.nhnacademy.minidooraydgateway.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/projects/{projectId}/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public String getTagsPage(@PathVariable Long projectId, Model model) {
        List<Tag> tags = tagService.getTagsByProjectId(projectId);
        model.addAttribute("tags", tags);
        model.addAttribute("tag", new Tag());
        model.addAttribute("projectId", projectId);
        return "project/tags";
    }

    @PostMapping
    public String handleCreateTag(@RequestParam("projectId") Long projectId, @RequestParam("name") String tagName) {
        Tag tag = new Tag();
        tag.setName(tagName);
        tag.setProjectId(projectId);
        tagService.createTag(tag);
        return "redirect:/projects/" + projectId + "/tags";
    }

    @PostMapping("/edit")
    public String handleEditTag(@RequestParam("projectId") Long projectId, @RequestParam("name") String tagName, @RequestParam("newName") String newTagName) {
        tagService.updateTagName(projectId, tagName, newTagName);
        return "redirect:/projects/" + projectId + "/tags";
    }

    @PostMapping("/{tagId}/delete")
    public String handleDeleteTag(@PathVariable Long projectId, @PathVariable Long tagId) {
        tagService.deleteTag(projectId, tagId);
        return "redirect:/projects/" + projectId + "/tags";
    }
}
