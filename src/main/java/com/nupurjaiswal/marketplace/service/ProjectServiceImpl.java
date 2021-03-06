package com.nupurjaiswal.marketplace.service;

import com.nupurjaiswal.marketplace.domain.Project;
import com.nupurjaiswal.marketplace.domain.Seller;
import com.nupurjaiswal.marketplace.exception.MarketplaceException;
import com.nupurjaiswal.marketplace.repository.ProjectRepository;
import com.nupurjaiswal.marketplace.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService{

    ProjectRepository projectRepository;
    SellerRepository sellerRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, SellerRepository sellerRepository) {
        this.projectRepository = projectRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public Optional<Project> getProjectById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public void saveProject(Project project) {
        Optional<Seller> findSeller = sellerRepository.findById(project.getSellerId());
        if (!findSeller.isPresent()) {
            throw new MarketplaceException("Please POST the seller entry in /sellers endpoint before adding the project.");
        } else{
            if(project.getDeadline().after(new Date())){
                projectRepository.saveAndFlush(project);
            }else{
                throw new MarketplaceException("Please enter the deadline of the Project greater than today's date.");
            }
        }
    }

  }
