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
    { header: "Nombre del Tipo", accessor: "nombreTipo", type: "text", filterable: true, filterKey: "nombre" },
    { header: "Estado", accessor: "estado", type: "text", filterable: true, filterKey: "estado" },
  ];



  return (
    <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
      <div className="px-4 py-6 md:px-6 xl:px-7.5 flex justify-between items-center">
        <h4 className="text-xl font-semibold text-black dark:text-white">
          Tipos de Intervenciones
        </h4>
        <button
          onClick={() => window.location.href = '/tipoIntervenciones/crear'}
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
    </div>
  );
};

export default ListarTiposIntervenciones; 