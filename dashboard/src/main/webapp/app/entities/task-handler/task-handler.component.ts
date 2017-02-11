import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { TaskHandler } from './task-handler.model';
import { TaskHandlerService } from './task-handler.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-task-handler',
    templateUrl: './task-handler.component.html'
})
export class TaskHandlerComponent implements OnInit, OnDestroy {
taskHandlers: TaskHandler[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private taskHandlerService: TaskHandlerService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['taskHandler']);
    }

    loadAll() {
        this.taskHandlerService.query().subscribe(
            (res: Response) => {
                this.taskHandlers = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTaskHandlers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: TaskHandler) {
        return item.id;
    }



    registerChangeInTaskHandlers() {
        this.eventSubscriber = this.eventManager.subscribe('taskHandlerListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
