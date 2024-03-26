import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './components/app/app.component';
import { UserAccountsComponent } from './components/user-accounts/user-accounts.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from './components/header/header.component';
import { AccountComponent } from './components/account/account.component';
import { AdminAccountsComponent } from './components/admin-accounts/admin-accounts.component';
import { JwtHelperService } from '@auth0/angular-jwt';



@NgModule({
  declarations: [
    AppComponent,
    UserAccountsComponent,
    LoginComponent,
    RegisterComponent,
    HeaderComponent,
    AccountComponent,
    AdminAccountsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  exports:[HeaderComponent],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
