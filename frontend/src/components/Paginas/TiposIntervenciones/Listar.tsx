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

  const [error, setError] = useState<string | null>(null);

  

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

      {error && (
        <div className="mx-4 mb-4 p-4 bg-red-100 text-red-700 rounded-lg">
          Error: {error}
        </div>
      )}

      <div className="p-4 md:p-6 xl:p-7.5">
        <DynamicTable
          columns={columns}
          data={tiposIntervenciones}
          withFilters={true}
          filterUrl="/tipoIntervenciones/filtrar"
          onDataUpdate={setTiposIntervenciones}
          withActions={true}
          deleteUrl="/tipoIntervenciones/inactivar"
          basePath="/tipoIntervenciones"
          onDelete={async (id) => {
            return await fetcher<{ message: string }>(`/tipoIntervenciones/inactivar?id=${id}`, { method: "DELETE" });
          }}
        />
      </div>
    </>

  );
};

export default ListarTiposIntervenciones;
