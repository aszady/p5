defmodule RestEndpoint.Notifier do

  use GenServer

  require Logger

  alias RestEndpoint.Http
  alias RestEndpoint.Config

  def get(subject, data) do
    GenServer.call(__MODULE__, {subject, data})
  end

  def start_link() do
    GenServer.start_link(__MODULE__, [], name: __MODULE__)
  end

  def init(_args) do
    {:ok, []}
  end

  def handle_call({:calculator, data}, _from, state) do
    base_url = Config.get_calculator_address()
    query = URI.encode_query(data)
    url = URI.merge(base_url, "/point?" <> query) |> to_string
    {:reply, Http.get(url), state}
  end

  def handle_call(_, _, state) do
    Logger.warn "Invalid call request"
    {:reply, :error, state}
  end
end
