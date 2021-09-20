package net.hackathlon.hcluser.user.registration;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user_info")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String first_name;
    @Column(nullable = false)
    private String last_name;
    @Column(nullable = false)
    private String email_id;
    @Column(nullable = false)
    private String primary_contact;
    @Column(nullable = false)
    private String secondary_contact;
    @Column(nullable = false)
    private String nid_name;
    @Column(nullable = false)
    private String nid_doc_id;

    private Date last_login;

    public UserInfo(UserInfoRegistrationRequest userInfoRegReq) {
        this.first_name = userInfoRegReq.getFirst_name();
        this.last_name = userInfoRegReq.getLast_name();
        this.nid_doc_id = userInfoRegReq.getNid_doc_id();
        this.primary_contact = userInfoRegReq.getPrimary_contact();
        this.secondary_contact = userInfoRegReq.getSecondary_contact();
        this.nid_name = userInfoRegReq.getNid_name();
        this.email_id = userInfoRegReq.getEmail_id();
    }

}