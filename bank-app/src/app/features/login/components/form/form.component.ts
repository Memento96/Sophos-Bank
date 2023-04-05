import { Component, OnInit } from '@angular/core';
import { Form } from '@angular/forms';
import { ProductsService } from 'src/app/core/services/products.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {


  //I am trying to implement the same process for the form as it works for the rest of them
  //Remember to implement that you will not create the body on the service but on the model.ts in /app
  //11:02 is the minute you left the video
  constructor(
    private productServices: ProductsService,
    private fb: Form
  ) { }

  ngOnInit(): void {
  }

}
