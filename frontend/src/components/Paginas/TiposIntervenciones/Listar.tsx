"use client";
import React, { useState } from "react";
import DynamicTable, { Column } from "@/components/Helpers/DynamicTable";

interface TipoIntervencion {
  id: number;
  nombreTipo: string;
  estado: string;
}

const ListarTiposIntervenciones: React.FC = () => {
  const [tipos, setTipos] = useState<TipoIntervencion[]>([]);

  const columns: Column<TipoIntervencion>[] = [
    { header: "Nombre del Tipo", accessor: "nombreTipo", type: "text", filterable: true },
    { header: "Estado", accessor: "estado", type: "text", filterable: true },
  ];

  return (
    <>
      <h4 className="text-xl font-semibold mb-4">Tipos de Intervenciones</h4>
      <DynamicTable
        columns={columns}
        data={tipos}
        withFilters={true}
        filterUrl="/tipoIntervenciones/filtrar"
        withActions={true}
        deleteUrl="/tipoIntervenciones/inactivar"
        basePath="/tipoIntervenciones"
        onDataUpdate={setTipos}
        initialFilters={{ estado: "ACTIVO" }}
        sendOnlyId={true}
      />
    </>
  );
};

export default ListarTiposIntervenciones;
