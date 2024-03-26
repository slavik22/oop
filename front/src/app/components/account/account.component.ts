import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from 'src/app/services/account.service';
import { PaymentService } from 'src/app/services/payment.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent {
  accountId: number = 0;
  account: any;
  depositAmount: number = 0;
  transferAmount: number = 0;
  recipientAccount: string = "";

  constructor(private route: ActivatedRoute, private accountService: AccountService, private paymentService: PaymentService ){

  }

  ngOnInit(): void {
    this.route.url.subscribe(data => {
      this.accountId = +data[1];
      this.getAccount(this.accountId)
    });
  }
  getAccount(accountId: number): void {
    this.accountService.getAccount(accountId)
      .subscribe(account => {
        this.account = account;
      });
    }
  depositMoney(): void {
      this.accountService.depositMoney(this.accountId, this.depositAmount)
          .subscribe(() => {
            this.depositAmount = 0;

          });
    }
    
  transferMoney(): void {
      this.paymentService.transferMoney(this.accountId, this.transferAmount, this.recipientAccount)
          .subscribe(() => {
            this.transferAmount = 0;
            this.recipientAccount = "";
          });
      }
}
