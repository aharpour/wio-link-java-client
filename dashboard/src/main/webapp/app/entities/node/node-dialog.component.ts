import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Node } from './node.model';
import { NodePopupService } from './node-popup.service';
import { NodeService } from './node.service';
import { Grove, GroveService } from '../grove';
@Component({
    selector: 'jhi-node-dialog',
    templateUrl: './node-dialog.component.html'
})
export class NodeDialogComponent implements OnInit {

    node: Node;
    authorities: any[];
    isSaving: boolean;

    groves: Grove[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private nodeService: NodeService,
        private groveService: GroveService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['node']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.groveService.query().subscribe(
            (res: Response) => { this.groves = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.node.nodeSn !== undefined) {
            this.nodeService.update(this.node)
                .subscribe((res: Node) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.nodeService.create(this.node)
                .subscribe((res: Node) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Node) {
        this.eventManager.broadcast({ name: 'nodeListModification', content: 'OK'});
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

    trackGroveById(index: number, item: Grove) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-node-popup',
    template: ''
})
export class NodePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private nodePopupService: NodePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.nodePopupService
                    .open(NodeDialogComponent, params['id']);
            } else {
                this.modalRef = this.nodePopupService
                    .open(NodeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
