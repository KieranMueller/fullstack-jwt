import { Component } from '@angular/core';
import { AxiosService } from '../axios.service';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css'],
})
export class ContentComponent {
  componentToShow = 'welcome';

  constructor(private axios: AxiosService) {}

  showComponent(componentToShow: string) {
    this.componentToShow = componentToShow;
  }

  onLogin(input: any) {
    this.axios
      .request('POST', '/login', {
        login: input.login,
        password: input.password,
      })
      .then((res) => {
        this.axios.setAuthToken(res.data.token);
        this.componentToShow = 'messages';
      });
  }

  onRegister(input: any) {
    this.axios
      .request('POST', '/register', {
        firstName: input.firstName,
        lastName: input.lastName,
        login: input.login,
        password: input.password,
      })
      .then((res) => {
        this.axios.setAuthToken(res.data.token);
        this.componentToShow = 'messages';
      });
  }
}
