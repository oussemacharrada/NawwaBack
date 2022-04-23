package com.dev.nawwa.repository;


import com.dev.nawwa.domain.ServiceProviderProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProviderProfile,Long>  {

}
