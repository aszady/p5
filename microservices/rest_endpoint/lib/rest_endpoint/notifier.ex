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

  def handle_call({:cache, position}, _from, state) do
    res = Http.post(Config.get_cache_address(), position)
    res2 = case res do 
             {:error, _} -> nil
             val -> val
           end
    {:reply, res2, state}
  end

  def handle_call({:calculator, data}, _from, state) do
    res = Http.post(Config.get_calculator_address(), data)
    {:reply, res, state}
  end

  def handle_call(_, _, state) do
    Logger.warn "Invalid call request"
    {:reply, :error, state}
  end
end
