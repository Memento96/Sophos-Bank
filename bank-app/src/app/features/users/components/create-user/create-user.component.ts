import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ProductsService } from 'src/app/core/services/products.service';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.scss']
})
export class CreateUserComponent implements OnInit {

  constructor(private productService: ProductsService) { }

  ngOnInit(): void {
    
  }

  create(data:any){

      this.productService.createClient(data).subscribe({
            next: () => {
              console.log("Client has been successfuly created")
            },
            error: (e) => {
              console.log(e.error.error);
            },
            complete: () => console.log('done'),
          });

  }

}
