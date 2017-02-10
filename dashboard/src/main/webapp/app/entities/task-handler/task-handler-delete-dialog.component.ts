import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { TaskHandler } from './task-handler.model';
import { TaskHandlerPopupService } from './task-handler-popup.service';
import { TaskHandlerService } from './task-handler.service';

@Component({
    selector: 'jhi-task-handler-delete-dialog',
    templateUrl: './task-handler-delete-dialog.component.html'
})
export class TaskHandlerDeleteDialogComponent {

    taskHandler: TaskHandler;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private taskHandlerService: TaskHandlerService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['taskHandler']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.taskHandlerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'taskHandlerListModification',
                content: 'Deleted an taskHandler'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-task-handler-delete-popup',
    template: ''
})
export class TaskHandlerDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private taskHandlerPopupService: TaskHandlerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.taskHandlerPopupService
                .open(TaskHandlerDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
