import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private apiUrl = '/api/recherche';
  //private apiUrl0 = environment.apiUrl;
  //private apiUrl = `${environment.apiUrl}/recherche`;

constructor(private http: HttpClient) { }

  /**
   * Search for chapters based on a search term
   * @param searchTerm The term to search for
   * @returns An Observable with the search results as a string
   */
  searchCodes(searchTerm: string): Observable<string> {
    //console.log("apiUrl :", this.apiUrl);
    //console.log("apiUrl0 :", this.apiUrl0);
    return this.http.get(`${this.apiUrl}/chapitres`, {
    //return this.http.get(`${this.apiUrl}/positions4`, {
    //return this.http.get(`${this.apiUrl}/positions6`, {
      params: { termeRecherche: searchTerm },
      responseType: 'text'
    });
  }
}
