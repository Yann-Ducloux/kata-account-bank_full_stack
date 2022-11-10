import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  constructor() { }
  accountBankId?: number;
  accountBankIds?: number[];
  public saveData(key: string, value: string) {
    localStorage.setItem(key, value);
  }

  public getData(key: string) {
    return localStorage.getItem(key)
  }
  public removeData(key: string) {
    localStorage.removeItem(key);
  }

  public clearData() {
    localStorage.clear();
  }
  public setaccountBankId(accountBankId: number) {
    this.accountBankId = accountBankId;
  }
  public getaccountBankId() {
    return this.accountBankId;
  }
  public setaccountBankIds(accountBankIds: number[]) {
    this.accountBankIds = accountBankIds;
  }
  public getaccountBankIds() {
    return this.accountBankId;
  }
}
