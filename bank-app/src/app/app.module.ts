import { Injectable, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CommonModule } from '@angular/common';
import { ProductModule } from './features/product/product.module';
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HTTP_INTERCEPTORS} from "@angular/common/http";
import { HeaderfooterModule } from './features/headerfooter/headerfooter.module';
import { Observable } from 'rxjs';


@Injectable()
export class NoopInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const newReq = req.clone({
      headers: req.headers.set('Access-Control-Allow-Origin', '*')
    });
    return next.handle(newReq);
  }
}



@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    ProductModule,
    ReactiveFormsModule,
    HttpClientModule,
    HeaderfooterModule
  ],
  providers: [ProductModule, { provide: HTTP_INTERCEPTORS, useClass: NoopInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
