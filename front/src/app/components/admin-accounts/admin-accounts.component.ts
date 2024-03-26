import { Component } from '@angular/core';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'app-admin-accounts',
  templateUrl: './admin-accounts.component.html',
  styleUrls: ['./admin-accounts.component.css']
})
export class AdminAccountsComponent {
  accounts: any[] = [];

  constructor(private accountService: AccountService) { }

  ngOnInit(): void {
    this.getAllAccounts();
  }

  getAllAccounts(): void {
    this.accountService.getAccounts()
      .subscribe(accounts => {
        this.accounts = accounts;
      });
  }

  unblockAccount(accountId: number): void {
    this.accountService.unlockAccount(accountId)
      .subscribe(() => {

      });
  }
}
