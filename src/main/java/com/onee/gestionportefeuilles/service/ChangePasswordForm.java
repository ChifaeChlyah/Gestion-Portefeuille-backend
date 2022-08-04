package com.onee.gestionportefeuilles.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordForm {
    String ancien_password;
    String nouveau_password;
    String confirm_nouveau_password;
}
