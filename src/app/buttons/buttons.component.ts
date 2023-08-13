import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-buttons',
  templateUrl: './buttons.component.html',
  styleUrls: ['./buttons.component.css'],
})
export class ButtonsComponent {
  @Output() loginEvent = new EventEmitter<any>();
  @Output() logoutEvent = new EventEmitter<any>();
}
