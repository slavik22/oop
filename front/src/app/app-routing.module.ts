import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { UserAccountsComponent } from './components/user-accounts/user-accounts.component';
import { AuthGuard } from './guards/auth.guard';
import { AccountComponent } from './components/account/account.component';
import { AdminAccountsComponent } from './components/admin-accounts/admin-accounts.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '', component: UserAccountsComponent, canActivate:[AuthGuard] },
  { path: 'account/:id', component: AccountComponent },
  { path: 'admin', component: AdminAccountsComponent }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
