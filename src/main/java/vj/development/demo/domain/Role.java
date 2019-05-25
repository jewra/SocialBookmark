package vj.development.demo.domain;

import javax.persistence.*;

@Entity
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private RoleType type;
    
    public RoleType getType() {
        return type;
    }
    
    public void setType(RoleType type) {
        this.type = type;
    }
    
    
    public Role() {
    }
    
    public enum RoleType {
        ROLE_USER, ROLE_ADMIN;
        
    }
}
