import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
})
export class LoginFormComponent {
  login = '';
  password = '';
  active = 'login';
  firstName = '';
  lastName = '';

  @Output() onSubmitLoginEvent = new EventEmitter<any>();
  @Output() onSubmitRegisterEvent = new EventEmitter<any>();

  onLoginTab() {
    this.active = 'login';
  }

  onRegisterTab() {
    this.active = 'register';
  }

  onSubmitLogin() {
    this.onSubmitLoginEvent.emit({
      login: this.login,
      password: this.password,
    });
  }

  onSubmitRegister() {
    this.onSubmitRegisterEvent.emit({
      firstName: this.firstName,
      lastName: this.lastName,
      login: this.login,
      password: this.password,
    });
  }
}
