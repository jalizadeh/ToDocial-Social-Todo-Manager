# Spring TO-DO Web Application
A To-Do manager with Spring and SQLite. I am trying to make it as much as possible complete, as an industry product, at the same time, a project to learn stuff.

### Features:
- Login / Sign up (multi-user)
- Todo management
- User profile
- Administrator dashboard


### Todos:

- [x] Initialize Spring project
- [x] Implement SQLite database
	- User
		- Role
			- Privilege
	- Todo
	- [ ] Set username & password
- [ ] General
	- [x] Header/Navigation/Footer fragments
		- Spring Security JSP Taglib is added for easier contorll of authenticated users
	- [x] Set page title
		- Each page&#39;s title is encapsulated in `model`
	- [x] Support UTF-8 encoding
	- [x] Handle exceptions with exact error reason
		- All exceptions will be redirected to `/error` showing the reason and what happened.
		- [ ] If any error occurs in subdirectories, the page is not shown correctly
	- [ ] Multi-language
- [x] Todos
	- [x] List of all todos
	- [x] Add/Update/Delete todos
	- [x] 1-click Todo's state change
	- [x] Search among todos
- [ ] Log in
	- Spring Security 
	- There are two roles `ROLE_USER` and `ROLE_ADMIN`
	- Everyone can access `Log in` or `Sign up`, but for accessing the todos, the user must log in or sign up first.
	- User can not login on multiple clients at the same time
	- [x] After login, user shouldn't be able to login again
	- [x] Field validation
	- [x] Log out
	- [ ] Forgot my password
		- The data is seperately stored in `PasswordResetToken` with 12-hour limit
		- [ ] Security Question
		- [ ] Field validation
	- [x] Remember me
		- Note that the check-box should be `... name="remember-me" ...`
		- [x] Client-side (cookie)
		- [ ] Server-side (persistence)
			- ❌ it seems db is not populated
	- [x] Logged in user, should not be able to reach pages like "Login", "Signup", ...
		- Managed in `SecurityConfiguration`, by defining the rules
- [ ] Signup new user
	- All new users are registered as `USER`
	- [x] Filed validation
	- [x] Security Question
		- Entity `SecurityQuestionDefinition` stores the questions.
		- Entity `SecurityQuestion` manages the relationship between the `User` and it&#39;s security `question_id` & `answer`.
		- It appears while signing up or changing password.
	- [x] Password
		- Passwords are encrypted with BCrypt 10
		- [x] Password strength and validation on front-end & back-end
			- It is done with `jQuery` & `pwstrength.js`
		- [x] Password confirmation
	- [x] Successful sign up
		- [x] Email verification
			- I used `smtp.gmail.com` as the server. Your email credentials `username` & `password` must be inserted in `application.properties`
			- The user has 24 hours to verify his account
			- [x] Token is valid
			- [x] Token is not valid
			- [x] Token is expired
			- [ ] User already activated
	- [x] Unseccessfull sign up
		- [ ] Show errors & exceptions
- [ ] User profile
	- [ ] Manage account
	- [ ] Delete account
- [ ] Admin panel
	- If user has `ROLE_ADMIN`, she can access the dashboard
	- [x] Manage users
		- [x] List of online users
		- [x] 1-click users's state change
		- [x] 1-click delete user
		- [ ] Modify user
		- [x] Add new user
			- [x] Handle mistakes like signing up a new user
			- [x] Email verification if user is suspended
			- [x] Set specified roles
				- [ ] Support multiple roles
	- [ ] Manage todos
		- [ ] Add new todo for any user
		- [ ] Modify todo
	- [ ] Settings
	- [ ] Limit `USER` access