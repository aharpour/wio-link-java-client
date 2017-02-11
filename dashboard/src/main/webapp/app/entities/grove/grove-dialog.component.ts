import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Grove } from './grove.model';
import { GrovePopupService } from './grove-popup.service';
import { GroveService } from './grove.service';
import { Node, NodeService } from '../node';
@Component({
    selector: 'jhi-grove-dialog',
    templateUrl: './grove-dialog.component.html'
})
export class GroveDialogComponent implements OnInit {

    grove: Grove;
    authorities: any[];
    isSaving: boolean;

    nodes: Node[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private groveService: GroveService,
        private nodeService: NodeService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['grove']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.nodeService.query().subscribe(
            (res: Response) => { this.nodes = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.grove.id !== undefined) {
            this.groveService.update(this.grove)
                .subscribe((res: Grove) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.groveService.create(this.grove)
                .subscribe((res: Grove) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Grove) {
        this.eventManager.broadcast({ name: 'groveListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackNodeById(index: number, item: Node) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-grove-popup',
    template: ''
})
export class GrovePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private grovePopupService: GrovePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.grovePopupService
                    .open(GroveDialogComponent, params['id']);
            } else {
                this.modalRef = this.grovePopupService
                    .open(GroveDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
