import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({ providedIn: 'root' })
export class LicenseService {
  private licenseStatus$: BehaviorSubject<any> = new BehaviorSubject(null);

  constructor(private http: HttpClient) {}

  fetchStatus() {
    this.http.get('/api/license/status').subscribe(status => {
      this.licenseStatus$.next(status);
      console.log("status :", status);
    });
  }

  getLicenseStatus(): Observable<any> {
    return this.licenseStatus$.asObservable();
  }
}

