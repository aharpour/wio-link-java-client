import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Task } from './task.model';
@Injectable()
export class TaskService {

    private resourceUrl = 'api/tasks';

    constructor(private http: Http) { }

    create(task: Task): Observable<Task> {
        let copy: Task = Object.assign({}, task);
        return this.http.post(this.resourceUrl, copy)
            .map((res: Response) => res.json())
            .catch(err => Observable.throw(err));
    }

    update(task: Task): Observable<Task> {
        let copy: Task = Object.assign({}, task);
        return this.http.put(this.resourceUrl, copy)
            .map((res: Response) => res.json())
            .catch(err => Observable.throw(err));
    }

    find(id: number): Observable<Task> {
        return this.http.get(`${this.resourceUrl}/${id}`)
            .map((res: Response) => res.json())
            .catch(err => Observable.throw(err));
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options);
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }



    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
