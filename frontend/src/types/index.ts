export interface EquipoModel {
    id: number | null;
    [key: string]: any;
}

export interface BajaEquipoModel {
    [key: string]: any;
}

export interface FuncionalidadModel {
    [key: string]: any;
}

export enum ReferrerEnum {
    ACTIVO = 'ACTIVO',
    INACTIVO = 'INACTIVO',
}
