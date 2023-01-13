import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ProductsService } from 'src/app/core/services/products.service';

@Component({
  selector: 'app-withdrawal',
  templateUrl: './withdrawal.component.html',
  styleUrls: ['./withdrawal.component.scss']
})
export class WithdrawalComponent implements OnInit {

  form: FormGroup;

  constructor(private productServices: ProductsService,
              private fb: FormBuilder) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      sender: [''],
      description: [''],
      transactionAmount: ['']
    });
  }


  send(){
    console.log('hello deposit')
    const body = {
      sender: this.form.get('sender')?.value,
      description: this.form.get('description')?.value,
      transactionType: "WITHDRAWAL",
      transactionAmount: this.form.get('transactionAmount')?.value
    }

    this.productServices.createTransaction(body).subscribe({
      next: () => {
        console.log("The withdrawal has been succesful")
      },
      error: (e) => {
        console.log(e.error.error);
      },
      complete: () => console.log('done'),
    });
  }

}
