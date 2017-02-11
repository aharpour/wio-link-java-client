import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { TaskHandler } from './task-handler.model';
import { TaskHandlerService } from './task-handler.service';

@Component({
    selector: 'jhi-task-handler-detail',
    templateUrl: './task-handler-detail.component.html'
})
export class TaskHandlerDetailComponent implements OnInit, OnDestroy {

    taskHandler: TaskHandler;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private taskHandlerService: TaskHandlerService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['taskHandler']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.taskHandlerService.find(id).subscribe(taskHandler => {
            this.taskHandler = taskHandler;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
