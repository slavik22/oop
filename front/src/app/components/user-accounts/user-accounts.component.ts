import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserService } from 'src/app/services/user.service';
import { AccountService } from 'src/app/services/account.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-accounts',
  templateUrl: './user-accounts.component.html',
  styleUrls: ['./user-accounts.component.css']
})
export class UserAccountsComponent {
  userAccounts: any[] = [];
  newAccount: any = { number: '', sum: 0 };


  constructor(private accountService: AccountService, private userService: UserService , private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.getUserAccounts();
  }

  getUserAccounts() {
    this.userService.getUserAccounts()
    .subscribe(accounts => {
      console.log(accounts)
      this.userAccounts = accounts;
    });
  }
  createAccount() {
    this.accountService.createAccount(this.newAccount)
      .subscribe(() => {
        this.newAccount = { number: '', sum: 0 };
        this.getUserAccounts();
      });
  }
  lock(number: any){
    this.accountService.lockAccount(number)
    .subscribe(() => {
      this.getUserAccounts();
    });
  }
  navigateToAccount(accountId: number) {
    this.router.navigate(['/account', accountId]);
  }
}
