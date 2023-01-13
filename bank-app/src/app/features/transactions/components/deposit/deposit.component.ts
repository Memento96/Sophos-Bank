import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ProductsService } from 'src/app/core/services/products.service';

@Component({
  selector: 'app-deposit',
  templateUrl: './deposit.component.html',
  styleUrls: ['./deposit.component.scss']
})
export class DepositComponent implements OnInit {

  form: FormGroup;

  constructor(private productServices: ProductsService,
              private fb: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      recipient: [''],
      description: [''],
      transactionAmount: ['']
    });
  }


  send(){
    console.log('hello deposit')
    const body = {
      recipient: this.form.get('recipient')?.value,
      description: this.form.get('description')?.value,
      transactionType: "DEPOSIT",
      transactionAmount: this.form.get('transactionAmount')?.value
    }

    this.productServices.createTransaction(body).subscribe({
      next: () => {
        console.log("The deposit has been succesful")
      },
      error: (e) => {
        console.log(e.error.error);
      },
      complete: () => console.log('done'),
    });
  }

}
