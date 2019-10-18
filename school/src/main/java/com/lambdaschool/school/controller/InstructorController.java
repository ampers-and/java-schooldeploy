package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.service.InstructorService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/instructors")
public class InstructorController
{
    @Autowired
    private InstructorService instructorService;

    @ApiOperation(value = "Adds a New Instructor.", notes = "The newly created instructor id will be sent in the location header.", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New Instructor Added Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error Adding New Instructor", response = ErrorDetail.class
            )})
    @PostMapping(value = "/instructor",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewInstructor(@Valid
                                           @RequestBody
                                              Instructor newInstructor) throws URISyntaxException
    {
        newInstructor = instructorService.save(newInstructor);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newStudentURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{instructorid}").buildAndExpand(newInstructor.getInstructid()).toUri();
        responseHeaders.setLocation(newStudentURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Edits Instructor associated with Sent Id.", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Instructor Edited Successfully", response = void.class),
            @ApiResponse(code = 404, message = "Instructor Not Found", response = ErrorDetail.class),
            @ApiResponse(code = 500, message = "Error Editing Instructor", response = ErrorDetail.class
            )})
    @PutMapping(value = "/instructor/{instructorid}")
    public ResponseEntity<?> updateInstructor(
            @RequestBody
                    Instructor updateInstructor,
            @PathVariable
                    long instructorid)
    {
        instructorService.update(updateInstructor, instructorid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "Deletes an Instructor by Id", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Instructor Deleted Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error deleting Instructor", response = ErrorDetail.class
            )})
    @DeleteMapping("/instructor/{instructorid}")
    public ResponseEntity<?> deleteInstructorById(
            @PathVariable
                    long instructorid)
    {
        instructorService.delete(instructorid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
