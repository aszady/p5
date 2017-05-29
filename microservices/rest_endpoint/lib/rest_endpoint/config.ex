defmodule RestEndpoint.Config do

  def get_cache_address() do
    get(:cache)
  end

  def get_calculator_address() do
    get(:calculator)
  end

  defp get(subject) do
    Application.get_env(:rest_endpoint, subject)
  end
end
