"use client";
import React, { useEffect, useState } from "react";
import fetcher from "@/components/Helpers/Fetcher";
import DynamicTable, { Column } from "@/components/Helpers/DynamicTable";
import { Perfil } from "@/types/Usuario";

const Listar: React.FC = () => {
  const [perfiles, setPerfiles] = useState<Perfil[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [allPerfiles, setAllPerfiles] = useState<Perfil[]>([]);

  const applyFilters = (filters: Record<string, string>) => {
    const nombre = filters["nombrePerfil"]?.toLowerCase() || "";
    const estado = filters["estado"] || "Todos";

    const filtrados = allPerfiles.filter((perfil) => {
      const matchNombre = perfil.nombrePerfil.toLowerCase().includes(nombre);
      const matchEstado = estado === "Todos" || perfil.estado === estado;
      return matchNombre && matchEstado;
    });

    setPerfiles(filtrados);
  };

  const handleSearch = async (filters: Record<string, string>) => {
    applyFilters(filters);
  };

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const data = await fetcher<Perfil[]>(`/perfiles/listar`, { method: "GET" });
        setAllPerfiles(data);
        setPerfiles(data);
      } catch (err: any) {
        setError(err.message);
      }
      setLoading(false);
    };

    fetchData();
  }, []);

  const columns: Column<Perfil>[] = [
    { header: "Nombre de Perfil", accessor: "nombrePerfil", type: "text", filterable: true },
    { header: "Estado", accessor: "estado", type: "text", filterable: true },
  ];

  return (
    <>
      {error && <p style={{ color: "red" }}>Error: {error}</p>}
      {loading ? (
        <p>Cargando...</p>
      ) : (
        <DynamicTable
          columns={columns}
          data={perfiles}
          withFilters={true}
          onSearch={handleSearch}
          withActions={true}
          deleteUrl="/perfiles/inactivar"
          basePath="/perfiles"
          onDelete={async (id) => {
            return await fetcher<{ message: string }>(`/perfiles/inactivar?id=${id}`, {
              method: "PUT",
            });
          }}
        />
      )}
    </>
  );
};

export default Listar;