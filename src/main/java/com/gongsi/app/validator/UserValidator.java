package com.gongsi.app.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gongsi.app.persistence.model.User;
import com.gongsi.app.persistence.UserDao;

@Service(value = "userValidator")
public class UserValidator implements Validator {
	@Autowired
	private UserDao userDao;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(User.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		if (userDao.findByLogin(user.getLogin()) != null) {
			errors.rejectValue("login", "", "non-unique login");
		}
		if (userDao.findByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "", "non-unique email");
		}
		if (user.getBirthday() == null) {
			errors.rejectValue("role", "", "enter a correct date, YYYY-MM-DD");
		}
	}
}
