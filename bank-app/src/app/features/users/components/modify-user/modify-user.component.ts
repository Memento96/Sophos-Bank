import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { ProductsService } from 'src/app/core/services/products.service';

@Component({
  selector: 'app-modify-user',
  templateUrl: './modify-user.component.html',
  styleUrls: ['./modify-user.component.scss']
})
export class ModifyUserComponent implements OnInit {

  form: FormGroup;
  
  user$: Observable<any>

  constructor(private productService: ProductsService,
              private fb: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      clientId: ['']
    })
  }

  getClientById(): void{
    const id = this.form.get('clientId')?.value
    this.user$ = this.productService.getClientById(id)
  }

  modify(data:any){
    const id = this.form.get('clientId')?.value
    this.productService.modifyClient(data, id).subscribe({
      next: () => {
        console.log("Client has been successfuly modified")
      },
      error: (e) => {
        console.log(e.error.error);
      },
      complete: () => console.log('done'),
    });
  }

  delete(){
    const id = this.form.get('clientId')?.value
    this.productService.deleteClient(id).subscribe({
      next: () => {
        console.log("Cliente deleted")
      },
      error: (e) => {
        console.log(e.error.error);
      },
      complete: () => console.log('done'),
    });
  }

}
