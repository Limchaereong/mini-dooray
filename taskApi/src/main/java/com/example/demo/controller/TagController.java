package com.example.demo.controller;

import com.example.demo.entity.Tag;
import com.example.demo.request.CreateTagRequest;
import com.example.demo.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<Tag>> getTagsByProjectId(@PathVariable Long projectId) {
        List<Tag> tags = tagService.getTagsByProjectId(projectId);
        return ResponseEntity.ok(tags);
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@PathVariable Long projectId, @Valid @RequestBody CreateTagRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Tag tag = tagService.createTag(projectId, request);
        return ResponseEntity.ok(tag);
    }

    @PutMapping("/{tagName}/name")
    public ResponseEntity<Void> updateTagName(@PathVariable Long projectId, @PathVariable String tagName, @RequestBody String newTagName) {
        tagService.updateTagName(projectId, tagName, newTagName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long tagId) {
        Tag tag = tagService.getTagById(tagId);
        return ResponseEntity.ok(tag);
    }
}
