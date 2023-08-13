import { Component, OnInit } from '@angular/core';
import { AxiosService } from '../axios.service';

@Component({
  selector: 'app-auth-content',
  templateUrl: './auth-content.component.html',
  styleUrls: ['./auth-content.component.css'],
})
export class AuthContentComponent implements OnInit {
  data: string[] = [];

  constructor(private axios: AxiosService) {}

  ngOnInit() {
    this.axios.request('GET', '/messages', null).then((res) => {
      console.log(res);
      this.data = res.data;
    });
  }
}
