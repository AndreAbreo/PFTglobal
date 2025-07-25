"use client";
import React, { useState } from "react";
import DynamicTable, { Column } from "@/components/Helpers/DynamicTable";

interface Perfil {
  id: number;
  nombrePerfil: string;
  estado: string;
}

interface Funcionalidad {
  id: number;
  nombreFuncionalidad: string;
  ruta: string;
  estado: string;
  perfiles: Perfil[];
}

const ListarFuncionalidades: React.FC = () => {
  const [funcionalidades, setFuncionalidades] = useState<Funcionalidad[]>([]);
  const [error] = useState<string | null>(null);

  const columns: Column<Funcionalidad>[] = [
    { header: "Nombre", accessor: "nombreFuncionalidad", type: "text", filterable: true, filterKey: "nombre" },
    { header: "Ruta", accessor: "ruta", type: "text", filterable: false },
    { header: "Estado", accessor: "estado", type: "text", filterable: true, filterKey: "estado" },
    {
      header: "Perfiles",
      accessor: (row) => row.perfiles.map(p => p.nombrePerfil).join(", "),
      type: "text",
      filterable: true,
      filterKey: "perfil"
    }
  ];

  return (
    <>
      {error && <p style={{ color: "red" }}>Error: {error}</p>}
      <DynamicTable
        columns={columns}
        data={funcionalidades}
        withFilters={true}
        filterUrl="/funcionalidades/filtrar"
        onDataUpdate={setFuncionalidades}
        withActions={true}
        deleteUrl="/funcionalidades/eliminar"
        basePath="/funcionalidades"
        sendOnlyId={false}
      />
    </>
  );
};

export default ListarFuncionalidades;