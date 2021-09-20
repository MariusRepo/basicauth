package net.hackathlon.hcluser.user.login;

import lombok.Data;

@Data
public class UserDetailsResponse {

    private final String nid_name;
    private final Long id;
    private final String username;
    private final String authority;
    private final String first_name;
    private final String last_name;
    private final String email_id;
    private final String nid_doc_id;
    private final String primary_contact;
    private final String secondary_contact;

    public UserDetailsResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.authority = user.getAuthority();
        this.first_name = user.getUserInfo().getFirst_name();
        this.last_name = user.getUserInfo().getLast_name();
        this.email_id = user.getUserInfo().getEmail_id();
        this.nid_name = user.getUserInfo().getNid_name();
        this.nid_doc_id = user.getUserInfo().getNid_doc_id();
        this.primary_contact = user.getUserInfo().getPrimary_contact();
        this.secondary_contact = user.getUserInfo().getSecondary_contact();
    }

}
