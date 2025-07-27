"use client";
import React, { useEffect, useState } from "react";
import fetcher from "@/components/Helpers/Fetcher";
import DynamicTable, { Column } from "@/components/Helpers/DynamicTable";

interface TipoEquipo {
  id: number;
  nombreTipo: string;
  estado: string;
}

const ListarTiposEquipos: React.FC = () => {
  const [tipos, setTipos] = useState<TipoEquipo[]>([]);
  const [filteredTipos, setFilteredTipos] = useState<TipoEquipo[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const [filters, setFilters] = useState({ nombreTipo: "", estado: "ACTIVO" });

  const fetchTipos = async () => {
    setLoading(true);
    try {
      const data = await fetcher<TipoEquipo[]>("/tipoEquipos/listar", { method: "GET" });
      setTipos(data);
      setFilteredTipos(applyFilters(data, filters));
    } catch (err: any) {
      setError(err.message);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchTipos();
  }, []);

  const applyFilters = (data: TipoEquipo[], filters: { nombreTipo: string; estado: string }) => {
    return data.filter((tipo) => {
      const matchNombre = tipo.nombreTipo.toLowerCase().includes(filters.nombreTipo.toLowerCase());
      const matchEstado = filters.estado ? tipo.estado === filters.estado : true;
      return matchNombre && matchEstado;
    });
  };

  const handleSearch = (newFilters: Record<string, string>) => {
    const updatedFilters = { ...filters, ...newFilters };
    setFilters(updatedFilters);
    setFilteredTipos(applyFilters(tipos, updatedFilters));
  };

  const columns: Column<TipoEquipo>[] = [
    { header: "Nombre", accessor: "nombreTipo", type: "text", filterable: true },
    { header: "Estado", accessor: "estado", type: "text", filterable: true },
  ];

  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [tipoAEliminar, setTipoAEliminar] = useState<TipoEquipo | null>(null);
  const [loadingDelete, setLoadingDelete] = useState(false);

  const handleDeleteWithModal = (id: number, row: TipoEquipo) => {
    setTipoAEliminar(row);
    setShowDeleteModal(true);
    return new Promise<{ message: string }>((resolve, reject) => {
      (window as any).__resolveDelete = resolve;
      (window as any).__rejectDelete = reject;
    });
  };

  const confirmarEliminacion = async () => {
    setLoadingDelete(true);
    try {
      await fetcher(`/tipoEquipos/inactivar?id=${tipoAEliminar?.id}`, {
        method: "DELETE",
      });
      setShowDeleteModal(false);
      (window as any).__resolveDelete({ message: "Tipo de equipo inactivado correctamente" });
      fetchTipos();
    } catch (err: any) {
      (window as any).__rejectDelete({ message: err.message || "Error al inactivar" });
    }
    setLoadingDelete(false);
  };

  return (
    <>
      {error && <p style={{ color: "red" }}>Error: {error}</p>}
      {loading ? (
        <p>Cargando...</p>
      ) : (
        <DynamicTable
          columns={columns}
          data={filteredTipos}
          withFilters={true}
          onSearch={handleSearch}
          withActions={true}
          deleteUrl="/tipoEquipos/inactivar"
          basePath="/tipoEquipos"
          onDelete={handleDeleteWithModal}
          initialFilters={filters}
          sendOnlyId={true}
        />
      )}

      {showDeleteModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-40">
          <div className="bg-white dark:bg-boxdark p-8 rounded-lg shadow-lg max-w-sm w-full">
            <h3 className="text-lg font-bold mb-4 text-red-600">Inactivar tipo de equipo</h3>
            <div className="mb-2">
              <label htmlFor="nombreTipo" className="block font-medium mb-1">Nombre</label>
              <input
                id="nombreTipo"
                type="text"
                value={tipoAEliminar?.nombreTipo || ""}
                readOnly
                className="w-full rounded border border-gray-300 p-2 bg-gray-100"
              />
            </div>
            <p className="mb-4">¿Está seguro de que desea inactivar este tipo de equipo?</p>
            <div className="flex justify-end gap-4">
              <button
                onClick={() => {
                  setShowDeleteModal(false);
                  (window as any).__rejectDelete({ message: "Inactivación cancelada" });
                }}
                className="px-4 py-2 rounded bg-gray-300 hover:bg-gray-400"
              >
                Volver
              </button>
              <button
                onClick={confirmarEliminacion}
                className="px-4 py-2 rounded bg-red-600 text-white hover:bg-red-700"
                disabled={loadingDelete}
              >
                {loadingDelete ? "Inactivando..." : "Confirmar"}
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default ListarTiposEquipos;
