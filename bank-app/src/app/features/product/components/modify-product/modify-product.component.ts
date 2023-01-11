import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-modify-product',
  templateUrl: './modify-product.component.html',
  styleUrls: ['./modify-product.component.scss']
})
export class ModifyProductComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  modify(data: any){
    console.log(data)
  }

}
