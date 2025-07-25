"use client";
import React, { useState } from "react";
import fetcher from "@/components/Helpers/Fetcher";
import DynamicTable, { Column } from "@/components/Helpers/DynamicTable";

interface TipoIntervencion {
  id: number;
  nombreTipo: string;
  estado: string;
}

const ListarTiposIntervenciones: React.FC = () => {
  const [tiposIntervenciones, setTiposIntervenciones] = useState<TipoIntervencion[]>([]);

  const columns: Column<TipoIntervencion>[] = [
    { header: "ID", accessor: "id", type: "number", filterable: false },
    { header: "Nombre del Tipo", accessor: "nombreTipo", type: "text", filterable: true },
    { header: "Estado", accessor: "estado", type: "text", filterable: true }
  ];

  return (
    <>
      <div className="flex justify-between items-center mb-4">
        <h4 className="text-xl font-semibold">Tipos de Intervenciones</h4>
        <button
          onClick={() => (window.location.href = "/tipoIntervenciones/crear")}
          className="inline-flex items-center justify-center rounded-md bg-primary px-4 py-2 text-center font-medium text-white hover:bg-opacity-90"
        >
          Crear Nuevo Tipo
        </button>
      </div>
      <DynamicTable
        columns={columns}
        data={tiposIntervenciones}
        withFilters={true}
        filterUrl="/tipoIntervenciones/filtrar"
        onDataUpdate={setTiposIntervenciones}
        withActions={true}
        deleteUrl="/tipoIntervenciones/inactivar"
        basePath="/tipoIntervenciones"
        confirmDeleteMessage="¿Está seguro que desea inactivar este tipo de intervención?"
        sendOnlyId={true}
      />
    </>
  );
};

export default ListarTiposIntervenciones;
