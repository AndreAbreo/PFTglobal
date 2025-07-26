"use client";
import React, { useEffect, useState } from "react";
import fetcher from "@/components/Helpers/Fetcher";
import DynamicTable, { Column } from "@/components/Helpers/DynamicTable";
import { formatDateForDisplay } from "@/components/Helpers/DateUtils";

interface Intervencion {
  id: number;
  motivo: string;
  fechaHora: string;
  comentarios: string;
  usuario?: string;
  tipo?: string;
  equipo?: string;
}

const ListarIntervenciones: React.FC = () => {
  const [intervenciones, setIntervenciones] = useState<Intervencion[]>([]);
  const [originalData, setOriginalData] = useState<Intervencion[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const handleSearch = async (filters: Record<string, string>) => {
    setLoading(true);
    const filtered = originalData.filter((row) => {
      return (
        (!filters.motivo || row.motivo.toLowerCase().includes(filters.motivo.toLowerCase())) &&
        (!filters.usuario || row.usuario?.toLowerCase().includes(filters.usuario.toLowerCase())) &&
        (!filters.tipo || row.tipo?.toLowerCase().includes(filters.tipo.toLowerCase())) &&
        (!filters.equipo || row.equipo?.toLowerCase().includes(filters.equipo.toLowerCase()))
      );
    });
    setIntervenciones(filtered);
    setLoading(false);
  };

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const data = await fetcher<any[]>("/intervenciones/listar", { method: "GET" });
        const parsed = data.map((row) => ({
          ...row,
          usuario: row.idUsuario ? `${row.idUsuario.nombre} ${row.idUsuario.apellido}` : "-",
          tipo: row.idTipo?.nombreTipo || "-",
          equipo: row.idEquipo ? `${row.idEquipo.idInterno} - ${row.idEquipo.nombre}` : "-",
        }));
        setOriginalData(parsed);
        setIntervenciones(parsed);
      } catch (err: any) {
        setError(err.message || "Error al obtener datos");
      }
      setLoading(false);
    };

    fetchData();
  }, []);

  const columns: Column<Intervencion>[] = [
    {
      header: "Fecha y Hora",
      accessor: (row) => formatDateForDisplay(new Date(row.fechaHora)),
      type: "text",
      filterable: false
    },
    { header: "Motivo", accessor: "motivo", type: "text", filterable: true },
    { header: "Tipo de Intervención", accessor: "tipo", type: "text", filterable: true },
    { header: "Equipo", accessor: "equipo", type: "text", filterable: true },
    { header: "Usuario", accessor: "usuario", type: "text", filterable: true },
    {
      header: "Comentarios",
      accessor: (row) => row.comentarios ?
        (row.comentarios.length > 50 ? row.comentarios.substring(0, 50) + "..." : row.comentarios)
        : "-",
      type: "text",
      filterable: false
    }
  ];

  return (
    <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
      <div className="px-4 py-6 md:px-6 xl:px-7.5">
        <h4 className="text-xl font-semibold text-black dark:text-white">
          Listado de Intervenciones
        </h4>
      </div>

      {error && (
        <div className="mx-4 mb-4 p-4 bg-red-100 text-red-700 rounded-lg">
          Error: {error}
        </div>
      )}

      <div className="p-4 md:p-6 xl:p-7.5">
        {loading ? (
          <div className="flex justify-center items-center h-32">
            <div className="text-gray-600">Cargando...</div>
          </div>
        ) : (
          <DynamicTable
            columns={columns}
            data={intervenciones}
            withFilters={true}
            onSearch={handleSearch}
            withActions={true}
            basePath="/intervenciones"
          />
        )}
      </div>
    </div>
  );
};

export default ListarIntervenciones;
