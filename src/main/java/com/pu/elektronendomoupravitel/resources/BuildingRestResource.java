package com.pu.elektronendomoupravitel.resources;

import org.springframework.web.bind.annotation.*;

@RestController
public class BuildingRestResource {
//
//    private final BuildingService buildingService;
//
//    public BuildingRestResource(BuildingService buildingService) {
//        this.buildingService = buildingService;
//    }
//
//    @GetMapping("/buildings")
//    public ResponseEntity<List<Building>> getBuildings() {
//        try {
//            List<Building> buildings = buildingService.fetchBuildings();
//
//            if (buildings == null && buildings.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(buildings, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/buildings/{id}")
//    public ResponseEntity<Building> getBuildingById(@PathVariable("id") long id) {
//        try {
//            Optional<Building> building = buildingService.getBuildingById(id);
//
//            if (building.isPresent()) {
//                return new ResponseEntity<>(building.get(), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PostMapping("/buildings")
//    public ResponseEntity<Building> createBuilding(@RequestBody Building building) {
//        try {
//            if (building == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            return new ResponseEntity<>(buildingService.newBuilding(building), HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PutMapping("/buildings/{id}")
//    public ResponseEntity<Building> updateBuilding(@PathVariable("id") long id, @RequestBody Building building) {
//        try {
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @DeleteMapping("/buildings{id}")
//    public ResponseEntity<HttpStatus> deleteBuilding(@PathVariable("id") long id) {
//        try {
//            buildingService.deleteBuildingById(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @DeleteMapping("/buildings")
//    public ResponseEntity<HttpStatus> deleteAllBuildings() {
//        try {
//            buildingService.deleteBuildings();
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
