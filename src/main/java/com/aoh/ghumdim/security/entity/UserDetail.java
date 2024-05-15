package com.aoh.ghumdim.security.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetail extends UserDetails  {

    String getRole();
    String getFirstNameForJwt();
    Integer getUserIdForJwt();


}
